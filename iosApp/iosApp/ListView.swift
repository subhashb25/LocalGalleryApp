import SwiftUI
import KMPNativeCoroutinesAsync
import Foundation
import KMPNativeCoroutinesCombine
import Combine
import shared // Import the shared KMP module

class ListViewModelWrapper: ObservableObject {
    private let viewModel: ListViewModel
    private var cancellables = Set<AnyCancellable>()

    @Published var objects: [MuseumObject] = []

    init() {
        do {
            self.viewModel = try KoinKt.provideListViewModel()
            observeObjects()
        } catch {
            print("Failed to get ViewModel: \(error)")
        }
    }

    private func observeObjects() {
        createStatePublisher(for: viewModel.objects)
            .receive(on: DispatchQueue.main)
            .sink(
                receiveValue: { [weak self] objects in
                    self?.objects = objects
                }
            )
            .store(in: &cancellables)
    }


}

struct ListView: View {
    
    @StateObject private var viewModelWrapper = ListViewModelWrapper()

    let columns = [
        GridItem(.adaptive(minimum: 120), alignment: .top)
    ]

    var body: some View {
        ZStack {
            if !viewModelWrapper.objects.isEmpty {
                if #available(iOS 16.0, *) {
                    NavigationStack {
                        ScrollView {
                            LazyVGrid(columns: columns, alignment: .leading, spacing: 20) {
                                ForEach(viewModelWrapper.objects, id: \.self) { item in
                                    NavigationLink(destination: DetailView(objectId: item.objectID)) {
                                        ObjectFrame(obj: item, onClick: {})
                                    }
                                    .buttonStyle(PlainButtonStyle())
                                }
                            }
                            .padding(.horizontal)
                        }
                    }
                } else {
                    NavigationView {
                        ScrollView {
                            LazyVGrid(columns: columns, alignment: .leading, spacing: 20) {
                                ForEach(viewModelWrapper.objects, id: \.self) { item in
                                    NavigationLink(destination: DetailView(objectId: item.objectID)) {
                                        ObjectFrame(obj: item, onClick: {})
                                    }
                                    .buttonStyle(PlainButtonStyle())
                                }
                            }
                            .padding(.horizontal)
                        }
                    }
                }
            } else {
                Text("No data available")
            }
        }
    }
}

struct ObjectFrame: View {
    let obj: MuseumObject
    let onClick: () -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            GeometryReader { geometry in
                AsyncImage(url: URL(string: obj.primaryImageSmall)) { phase in
                    switch phase {
                    case .empty:
                        ProgressView()
                            .frame(width: geometry.size.width, height: geometry.size.width)
                    case .success(let image):
                        image
                            .resizable()
                            .scaledToFill()
                            .frame(width: geometry.size.width, height: geometry.size.width)
                            .clipped()
                            .aspectRatio(1, contentMode: .fill)
                    default:
                        EmptyView()
                            .frame(width: geometry.size.width, height: geometry.size.width)
                    }
                }
            }
            .aspectRatio(1, contentMode: .fit)

            Text(obj.title)
                .font(.headline)

            Text(obj.artistDisplayName)
                .font(.subheadline)

            Text(obj.objectDate)
                .font(.caption)
        }
    }
}

